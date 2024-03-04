package io.smartir.smartir.website.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelperFunctionsTest {

    private final List<String> given = List.of("CrimsonFox21",
            "LuckyJazz98",
            "SilverDragon44",
            "TechNinja77",
            "OceanBreeze123",
            "EagleEye86",
            "SunnySideUp55",
            "MoonlightShadow28",
            "StarGazer69",
            "NeonSpectre42");


    @Test
    @DisplayName("Pagination common function")
    public void testPaginationNormalSituation() {


        //given
        HelperFunctions helperFunctions = new HelperFunctions();

        var page = PageRequest.of(0, 2);

        //when
        var paginated = helperFunctions.makingPagination(given, page);

        //then
        assertEquals(2, paginated.getContent().size());
        assertEquals("CrimsonFox21", paginated.getContent().get(0));
        assertEquals("LuckyJazz98", paginated.getContent().get(1));
        assertEquals(10, paginated.getTotalElements());
        assertEquals(page, paginated.getPageable());
    }

    @Test
    @DisplayName("Pagination last page")
    public void testLastPage() {

        //given
        HelperFunctions helperFunctions = new HelperFunctions();
        var page = PageRequest.of(3, 3);
        //when
        var paginated = helperFunctions.makingPagination(given, page);
        //then
        assertEquals(1, paginated.getContent().size());
        assertThat(paginated.getContent().size()).isEqualTo(1);
        assertEquals("NeonSpectre42", paginated.getContent().get(0));
        assertEquals(10, paginated.getTotalElements());
        assertEquals(page, paginated.getPageable());
    }

    @Test
    @DisplayName("Page number is out of range")
    public void testOutOfRange() {
        //given
        HelperFunctions helperFunctions = new HelperFunctions();
        var page = PageRequest.of(5, 2);

        //when
        var pagination = helperFunctions.makingPagination(given, page);

        //then
        assertEquals(0, pagination.getContent().size());
        assertEquals(10, pagination.getTotalElements());
        assertEquals(page, pagination.getPageable());
    }

    @Test
    @DisplayName("Pagination with empty list")
    public void testEmptyList() {
        //given
        HelperFunctions helperFunctions = new HelperFunctions();
        var page = PageRequest.of(0, 5);

        //when
        var paginated = helperFunctions.makingPagination(List.of(), page);
        //then
        assertEquals(0, paginated.getContent().size());
        assertEquals(0, paginated.getTotalElements());
        assertEquals(page, paginated.getPageable());
    }

}