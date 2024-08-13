package com.ahmadsodik.sarpras.data.source.firebase

import android.util.Log
import com.ahmadsodik.sarpras.data.source.model.Barang
import com.ahmadsodik.sarpras.data.source.model.Pinjam
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor() : FirebaseService {

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    override suspend fun login(email: String, password: String): FirebaseUser? {
        return auth.signInWithEmailAndPassword(email, password)
            .await()
            .user
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun ambilDataKelas(): List<Barang?> {
        val result = firestore.collection("kelas")
            .get()
            .await()

        return result.documents.map {
            it.toObject(Barang::class.java)
        }
    }

    override suspend fun ambilDataLab(): List<Barang?> {
        val result = firestore.collection("laboratorium")
            .get()
            .await()

        return result.documents.map {
            it.toObject(Barang::class.java)
        }
    }

    override suspend fun inputPinjaman(namaPeralatan: String, data: Pinjam) {
        val jumlahBarang = firestore.collection(namaPeralatan).document(data.id.toString())
            .get()
            .await()
            .toObject(Barang::class.java)
            ?.jumlah

        // Cek apakah barang yang akan dipinjam kuotanya masih mencukupi
        if ((jumlahBarang?.minus(data.jumlahPinjamBarang) ?: 0) < 0 || jumlahBarang == 0) {
            throw Exception("Jumlah barang yang akan dipinjam tidak mencukupi")
        }

        firestore.collection(namaPeralatan).document(data.id.toString())
            .update("jumlah", FieldValue.increment(-data.jumlahPinjamBarang.toLong()))
            .await()

        val doc = firestore.collection("pinjam")
            .document()
        doc.set(data.copy(documentId = doc.id))
            .await()
    }

    override suspend fun ambilRiwayatSedangDipinjam(): List<Pinjam?> {
        return firestore.collection("pinjam")
            .whereEqualTo("status", "sedang dipinjam")
            .get()
            .await()
            .toObjects(Pinjam::class.java)
    }

    override suspend fun ambilRiwayatSelesaiDipinjam(): List<Pinjam?> {
        return firestore.collection("pinjam")
            .whereEqualTo("status", "selesai dipinjam")
            .get()
            .await()
            .toObjects(Pinjam::class.java)
    }

    override suspend fun tandaiPinjamanSelesai(pinjam: Pinjam?) {
        // Mengembalikan jumlah stok setelah peminjaman selesai
        firestore.collection(pinjam?.namaPeralatan.toString()).document(pinjam?.id.toString())
            .update("jumlah", FieldValue.increment(pinjam?.jumlahPinjamBarang?.toLong() ?: 0))
            .await()

        //mengubah status menjadi selesai dipinjam
        firestore.collection("pinjam")
            .document(pinjam?.documentId.toString())
            .update("status", "selesai dipinjam")
            .await()
    }
}