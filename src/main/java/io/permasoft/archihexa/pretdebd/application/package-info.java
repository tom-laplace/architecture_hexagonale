/**
 * Application "layer" that is responsible for:
 * <ul>
 * <li>exposing use cases <b>without any constraint other than the business language and the programming langage</b>.</li>
 * <li>defining interfaces for external dependencies <b>without any constraint other than the business language and the programming langage</b>.</li>
 * <li>implementing use cases requiring previous interfaces, delegating business rules to the domain model, and handling the transaction when needed.</li>
 * </ul>
 */
package io.permasoft.archihexa.pretdebd.application;

