package com.grayvulf.routes

import com.grayvulf.models.Customer
import com.grayvulf.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(){

    route("/customer"){
        
        get {
            if(customerStorage.isNotEmpty()){
                call.respond(customerStorage)
            }else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }

        }

        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )

            call.respond(customer)
        }

        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)

        }

        delete("{id?}") {

        }
    }
}