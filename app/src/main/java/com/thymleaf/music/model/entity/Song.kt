package com.thymleaf.music.model.entity

data class Song(
        val al: Album,
        val ar: List<Artist>,
        val cp: Int,
        val id: Int,
        val mark: Int,
        val mst: Int,
        val mv: Int,
        val name: String,
        val pop: Int,
        val privilege: Privilege,
        val publishTime: Long,
        var detail: SongDetail? = null
) {


    override fun toString(): String {
        return "Song(album=$al, artist=$ar, cp=$cp, id=$id, mark=$mark, mst=$mst, mv=$mv, name='$name', pop=$pop, privilege=$privilege, publishTime=$publishTime)"
    }
}

data class Album(
        val id: Int,
        val name: String,
        val pic: Long,
        val picUrl: String,
        val pic_str: String
)

data class Artist(
        val id: Int,
        val name: String
)

data class Privilege(
        val cp: Int,
        val cs: Boolean,
        val dl: Int,
        val downloadMaxbr: Int,
        val fee: Int,
        val fl: Int,
        val flag: Int,
        val id: Int,
        val maxbr: Int,
        val payed: Int,
        val pl: Int,
        val playMaxbr: Int,
        val preSell: Boolean,
        val sp: Int,
        val st: Int,
        val subp: Int,
        val toast: Boolean
)

data class SongDetail(
    val br: Int,
    val canExtend: Boolean,
    val code: Int,
    val encodeType: Any,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: FreeTrialInfo,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val gain: Int,
    val id: Int,
    val level: Any,
    val md5: String,
    val payed: Int,
    val size: Int,
    val type: String,
    val uf: Any,
    val url: String,
    val urlSource: Int
){
    override fun toString(): String {
        return "SongDetail(id=$id, url='$url')"
    }
}

data class FreeTimeTrialPrivilege(
    val remainTime: Int,
    val resConsumable: Boolean,
    val type: Int,
    val userConsumable: Boolean
)

data class FreeTrialInfo(
    val end: Int,
    val start: Int
)

data class FreeTrialPrivilege(
    val resConsumable: Boolean,
    val userConsumable: Boolean
)