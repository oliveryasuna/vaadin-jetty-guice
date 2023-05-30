package com.oliveryasuna.example.vjg.guice.annotation;

import com.google.inject.ScopeAnnotation;

import java.lang.annotation.*;

/**
 * Scopes beans to the current {@link com.vaadin.flow.server.VaadinSession}.
 *
 * @author Oliver Yasuna
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ScopeAnnotation
public @interface SessionScope {

}
