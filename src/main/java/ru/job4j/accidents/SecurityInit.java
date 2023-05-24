package ru.job4j.accidents;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Spring использует тот же подход, что и Servlet.
 * Внутри используется Filter, который проверяет входящие запросы и отдает jsessionId.
 * Подключение фильтров происходит за счет добавления класса - SecurityInit.
 * Он подключает DelegatingFilterProxy. Это главный фильтр, в котором идет обработка запросов.
 * Tomcat автоматически определяет такой класс и подключает эти фильтры.
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {

}
