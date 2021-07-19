package ru.varasoft.pictureoftheday.model.pod

class RoomPODCache(private val db: Database) : IPODCache {
    override fun getPOD(date: String): PODServerResponseData {
        val roomPod = date?.let { db.podDao.findByDate(date) }
            ?: throw RuntimeException("No such picture in cache")
        val pod = PODServerResponseData(
            roomPod.copyright,
            roomPod.date,
            roomPod.explanation,
            roomPod.mediaType,
            roomPod.thumbnailUrl,
            roomPod.title,
            roomPod.url,
            roomPod.hdurl
        )
        return pod
    }

    override fun insertPOD(pod: PODServerResponseData, date: String){
        val roomPOD = RoomPODServerResponseData(
            0,
            pod.copyright,
            pod.date,
            pod.explanation,
            pod.mediaType,
            pod.thumbnailUrl,
            pod.title,
            pod.url,
            pod.hdurl
        )
        db.podDao.insert(roomPOD)
    }
}