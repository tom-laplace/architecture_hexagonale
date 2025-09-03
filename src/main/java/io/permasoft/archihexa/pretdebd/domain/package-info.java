/**
 * Domain "layer" is responsible for representing all and only necessary concepts to compute business rules as:
 * <ul>
 * <li>names</li>
 * <li>behaviors</li>
 * <li>relations</li>
 * <li>constraints</li>
 * </ul>
 * It is made of value objects, entities, aggregates and factories.
 * All names of any hing come from business langage (classes, fields, methods, etc).
 * <h2>Value objects</h2>
 * Value objects are sets of cohesive data that may be primitive type with a name, or other value objects or a wrapped collection.
 * Value objects may have non-public business methods that return a new value object with new values.
 * Value objects may contain a public builder pattern using only primitive types or other value objects.
 * Value object are identified with their value, meaning equals and hashcode method work with all their internal values.
 * <h2>Entities</h2>
 * Entities are sets of cohesive values objects (and no primitive) or others entities within the same bounded context, or value object that references ids of entities in other bounded contexts.
 * Entities may have non-public business methods that should return a new value object by comptuing business rules, but it often updates internal state.
 * Entities may contain a package builder pattern used only by application layer.
 * Entities are identified with unique identifier, meaning equals and hashcode method work only with their id and not their content.
 * <h2>Aggregates</h2>
 * Aggregates are an entity that represents a transaction boundary within its owning bounded context.
 * Aggregates may have both public and non-public business methods that should return a new value object by computing business rules, but it often updates internal state.
 * Aggregates'public methods are to be called by application layer.
 * <h2>Factories</h2>
 * Aggregates and entities are created and loaded by application layer as calling use cases may have an id, but they no reason to know the internal state to call a use case that update some part of an aggregate.
 * Value objects are created by all layers using builders only (the constructor is the simplest kind of builder)
 */
package io.permasoft.archihexa.pretdebd.domain;