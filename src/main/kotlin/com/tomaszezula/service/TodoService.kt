package com.tomaszezula.service

import com.tomaszezula.model.Todo
import com.tomaszezula.model.TodoRepository
import com.tomaszezula.model.asDomain

interface TodoService {
    suspend fun findAll(): List<Todo>
    suspend fun create(task: String, userId: UserId): Todo
}

class TodoServiceImpl(
    private val todoRepository: TodoRepository
) : TodoService {
    override suspend fun findAll(): List<Todo> {
        return todoRepository.findAll().map { it.asDomain() }
    }

    override suspend fun create(task: String, userId: UserId): Todo =
        todoRepository.create(task, userId).asDomain()

}