package com.lovisgod.tranctiontest

import org.jpos.q2.Q2
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TranctiontestApplication: CommandLineRunner {
	override fun run(vararg args: String?) {
		val q2 = Q2(args)
		q2.start()
	}

}

fun main(args: Array<String>) {
	runApplication<TranctiontestApplication>(*args)
}