/**
 * Infrastructure is the generic name for the adapters that calls the application and the adapters called by the application.
 * You may find adapter that both call the application and are called by it. (any exchange that needs business rules in both ways: place a bet, you win or lose the bet)
 *
 * <h2>Common to all infrastructure</h2>
 * Their main responsibility is to glue together one external system (Rest APIs, message queues, database, search, batches, etc.) with the application's use cases.
 * Their main objective is to remove any technicalities (or technical complexity) from the application and domain code so application and domain only deal with programming langage, business langage, complexity and business complexity.
 * <h2>Adapters that call the application</h2>
 * These adapters are called by external systems to call the application.
 * They are named left adapters or adapter on the input side.
 * They handle things like rest APIs, listening to message queues, batches, etc.
 * They take external objects as input to map them to the business objects.
 * They use value object builders to call application use cases with validated data.
 * They also handle authentication and authorization when there is no business rules to compute the autorization.
 * <h2>Adapters that are called by the application</h2>
 * These adapters are called by the application to call the external system.
 * They are named right adapters or adapter on the output side.
 * They handle things like posting to message queues, search, databases, etc.
 * They take business objects as input to map them to the next system.
 * They may propagate authentication tokens, etc.
 */
package io.permasoft.archihexa.pretdebd.infrastructure;