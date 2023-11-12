package com.tomaszezula.plugins

import com.tomaszezula.model.Todo
import kotlinx.html.*

fun HTML.homePage(currentUser: String) {
    body {
        p { +"Hello, $currentUser!" }
        p {
            a(href = "/logout") { +"Log out!" }
        }
        footer()
    }
}

fun HTML.landingPage() {
    body {
        p {
            +"You are not logged in. Please "
            a(href = "/login") { +"log in" }
            +" or "
            a(href = "/register") { +"register" }
            +" to continue."
        }
        footer()
    }
}

fun HTML.loginForm() {
    body {
        form(action = "/login", method = FormMethod.post) {
            p {
                input(type = InputType.text, name = "email") {
                    placeholder = "Email"
                }
            }
            p {
                input(type = InputType.password, name = "password") {
                    placeholder = "Password"
                }
            }
            p {
                input(type = InputType.submit, name = "submit") {
                    value = "Login"
                }
            }
        }
        footer()
    }
}

fun HTML.registrationForm() {
    body {
        form(action = "/register", method = FormMethod.post) {
            p {
                input(type = InputType.text, name = "email") {
                    placeholder = "Email"
                }
            }
            p {
                input(type = InputType.password, name = "password") {
                    placeholder = "Password"
                }
            }
            p {
                input(type = InputType.submit, name = "submit") {
                    value = "Register"
                }
            }
        }
        footer()
    }
}

fun HTML.todoList(todos: List<Todo>) {
    body {
        table {
            thead {
                tr {
                    th { +"User" }
                    th { +"Task" }
                    th { +"Done" }
                }
            }
            tbody {
                todos.forEach { todo ->
                    tr {
                        td { +todo.userId }
                        td { +todo.task }
                        td { +todo.isDone.toString() }
                    }
                }
            }
        }
        form(action = "/todos", method = FormMethod.post) {
            p {
                input(type = InputType.text, name = "task") {
                    placeholder = "Task"
                }
            }
            p {
                input(type = InputType.submit, name = "submit") {
                    value = "Add"
                }
            }
        }
        footer()
    }
}
fun BODY.footer() {
    p {
        a(href = "/") { +"Go home" }
        +" | "
        a(href = "/todos") { +"See all todos" }
    }
}
