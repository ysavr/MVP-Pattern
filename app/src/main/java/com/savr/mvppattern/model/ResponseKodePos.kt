package com.savr.mvppattern.model

import com.google.gson.annotations.SerializedName

data class ResponseKodePos(

	@field:SerializedName("kecamatan")
	val kecamatan: String? = null,

	@field:SerializedName("kodepos")
	val kodepos: String? = null,

	@field:SerializedName("kelurahan")
	val kelurahan: String? = null
)