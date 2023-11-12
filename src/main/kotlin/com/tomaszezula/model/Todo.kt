package com.tomaszezula.model

import com.tomaszezula.service.UserId
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TodoRecord(
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("user_id")
    val userId: String,
    @SerialName("task")
    val task: String,
    @SerialName("is_done")
    val isDone: Boolean
)

@Serializable
data class Todo(
    val id: String,
    val userId: String,
    val task: String,
    val isDone: Boolean
)

fun TodoRecord.asDomain(): Todo =
    Todo(
        id = id,
        userId = userId,
        task = task,
        isDone = isDone
    )

interface TodoRepository {
    suspend fun findAll(): List<TodoRecord>
    suspend fun create(task: String, userId: UserId): TodoRecord
}

class TodoRepositoryImpl(
    private val client: SupabaseClient
) : TodoRepository {
    override suspend fun findAll(): List<TodoRecord> {
        val recs = client.postgrest["todos"]
            .select()
            return recs.decodeList<TodoRecord>()

    }

    override suspend fun create(task: String, userId: UserId): TodoRecord =
        client.postgrest["todos"]
            .insert(TodoRecord(userId = userId, task = task, isDone = false))
            .decodeSingle()

}