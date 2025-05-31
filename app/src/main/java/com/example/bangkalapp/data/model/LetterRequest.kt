package com.example.bangkalapp.data.model

data class LetterRequest(
    val id: Int? = null,
    val user_id: Int? = null,
    val letter_type_id: Int? = null,
    val nik: String? = null,
    val address: String? = null,
    val gender: String? = null,
    val place_of_birth: String? = null,
    val citizenship: String? = null,
    val religion: String? = null,
    val father_name: String? = null,
    val mother_name: String? = null,
    val status: String? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val user: User? = null,
    val letter_type: LetterType? = null
)
