package com.tomaszezula

import com.tomaszezula.model.TodoRepositoryImpl
import com.tomaszezula.plugins.configureRouting
import com.tomaszezula.plugins.configureSerialization
import com.tomaszezula.service.TodoServiceImpl
import com.tomaszezula.service.UserServiceImpl
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val client = createSupabaseClient(
        supabaseUrl = environment.config.property("ktor.supabase.url").getString(),
        supabaseKey = environment.config.property("ktor.supabase.key").getString()
    ) {
        install(GoTrue)
        install(Postgrest)
    }
    val todoRepository = TodoRepositoryImpl(client)
    val todoService = TodoServiceImpl(todoRepository)
    val userService = UserServiceImpl(client)
    configureRouting(todoService, userService)
    configureSerialization()
}
