package com.weedow.spring.data.search.fieldpath

import com.neovisionaries.i18n.CountryCode
import com.nhaarman.mockitokotlin2.whenever
import com.weedow.spring.data.search.alias.AliasResolutionService
import com.weedow.spring.data.search.common.model.Address
import com.weedow.spring.data.search.common.model.Person
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class FieldPathResolverImplTest {

    @Mock
    lateinit var aliasResolutionService: AliasResolutionService

    @InjectMocks
    lateinit var fieldPathResolver: FieldPathResolverImpl

    @Test
    fun resolveFieldPath_for_direct_field() {
        val rootClass = Person::class.java
        val fieldPath = "firstName"

        whenever(aliasResolutionService.resolve(rootClass, "firstName")).thenReturn("firstName")

        val fieldPathInfo = fieldPathResolver.resolveFieldPath(rootClass, fieldPath)

        val resolvedFieldPath = fieldPath
        assertThat(fieldPathInfo).isEqualTo(FieldPathInfo(resolvedFieldPath, "firstName", String::class.java, rootClass))
    }

    @Test
    fun resolveFieldPath_for_subfield() {
        val rootClass = Person::class.java
        val fieldPath = "address.country"
        val parentClass = Address::class.java

        whenever(aliasResolutionService.resolve(rootClass, "address")).thenReturn("addressEntities")
        whenever(aliasResolutionService.resolve(parentClass, "country")).thenReturn("country")

        val fieldPathInfo = fieldPathResolver.resolveFieldPath(rootClass, fieldPath)

        val resolvedFieldPath = "addressEntities.country"
        assertThat(fieldPathInfo).isEqualTo(FieldPathInfo(resolvedFieldPath, "country", CountryCode::class.java, parentClass))
    }

    @Test
    fun throw_exception_when_field_path_unresolved() {
        val rootClass = Person::class.java
        val fieldPath = "unknown"

        whenever(aliasResolutionService.resolve(rootClass, "unknown")).thenReturn("unknown")

        assertThatThrownBy { fieldPathResolver.resolveFieldPath(rootClass, fieldPath) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessageStartingWith("Could not resolve the field path [$fieldPath] from [$rootClass]")
                .hasCauseInstanceOf(NoSuchFieldException::class.java)
    }
}