package com.raytotti.convertcurrencykt.commons.configuration

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class MessagesConfig {
    @Bean
    fun messageSource(): MessageSource = messageSourceConfig("messages/exception/messages")

    @Bean
    fun getValidatorFactoryBean(): LocalValidatorFactoryBean {
        val factoryBean = LocalValidatorFactoryBean()
        factoryBean.setValidationMessageSource(messageSourceConfig("messages/validation/messages"))
        return factoryBean
    }

    fun messageSourceConfig(path: String): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename(path)
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setUseCodeAsDefaultMessage(true)
        return messageSource
    }
}