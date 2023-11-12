package com.tomaszezula.plugins

import com.tomaszezula.service.TodoService
import com.tomaszezula.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    todoService: TodoService, userService: UserService
) {
    routing {
        get("/") {
            val currentUser = userService.currentUser()
            call.respondHtml(HttpStatusCode.OK) {
                if (currentUser == null) landingPage() else homePage(currentUser)
            }
        }
        route("/login") {
            get {
                call.respondHtml(HttpStatusCode.OK) {
                    loginForm()
                }
            }
            post {
                val formParameters = call.receiveParameters()
                val email = formParameters["email"] ?: throw IllegalArgumentException("Email is required.")
                val password = formParameters["password"] ?: throw IllegalArgumentException("Password is required.")
                userService.signIn(email, password)
                call.respondRedirect("/")
            }
        }
        route("/logout") {
            get {
                userService.signOut()
                call.respondRedirect("/")
            }
        }
        route("/register") {
            get {
                call.respondHtml(HttpStatusCode.OK) {
                    registrationForm()
                }
            }
            post {
                val formParameters = call.receiveParameters()
                val email = formParameters["email"] ?: throw IllegalArgumentException("Email is required.")
                val password = formParameters["password"] ?: throw IllegalArgumentException("Password is required.")
                userService.signUp(email, password)
                call.respondRedirect("/")
            }
        }
        route("/todos") {
            get {
                val todos = todoService.findAll()
                call.respondHtml(HttpStatusCode.OK) {
                    todoList(todos)
                }
            }
            post {
                val currentUser = userService.currentUser()
                if (currentUser == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                } else {
                    val formParameters = call.receiveParameters()
                    val task = formParameters["task"] ?: throw IllegalArgumentException("Title is required.")
                    todoService.create(task, currentUser)
                    call.respondRedirect("/todos")
                }
            }
        }
    }
}