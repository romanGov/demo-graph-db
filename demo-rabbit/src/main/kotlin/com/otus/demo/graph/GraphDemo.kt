package com.otus.demo.graph

import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal

val cluster by lazy {
    Cluster.build().apply {
        addContactPoint("localhost")
        port(8182)
        credentials("root", "playwithdata")
        enableSsl(false)
    }.create()
}
private val g by lazy {
    traversal()
    .withRemote(DriverRemoteConnection.using(cluster))
}
fun main(){
    val business = g.addV("Business")
        .property("legal_name","t76767e5st")
        .property("id","121212")
        .property("email","5555555@mail.com")
        .next()
    val person = g.addV("Person")
        .property("first_name","Vova44444")
        .property("id","7774")
        .property("email","v44444ova@mail.com")
        .next()
    val edgeGraphTraversal = g
        .addE("worksIn")
        .from(person).to(business)
        .next()
    println("success $edgeGraphTraversal")
}