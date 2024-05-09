package com.lovisgod.tranctiontest

import org.jpos.iso.ISOUtil
import org.jpos.iso.MUX
import org.jpos.q2.Q2
import org.jpos.q2.iso.QMUX
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableAsync
class TranctiontestApplication {

	@Bean
	fun q2(): Q2 {
		val q2 = Q2("deploy")
		q2.start()
		return q2
	}

	@Bean
	fun mux(q2: Q2): MUX {
		while (!q2.ready()) {
			ISOUtil.sleep(10)
		}
		return QMUX.getMUX("nibss_mux")
	}
}

fun main(args: Array<String>) {
	runApplication<TranctiontestApplication>(*args)
}