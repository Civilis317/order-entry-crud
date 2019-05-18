package net.playground.orderservice.rest.controller;

import net.playground.orderservice.exception.OrderServiceException;
import net.playground.orderservice.rest.io.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Value("${app-settings.page-size}")
    private int pageSize;

    @ExceptionHandler
    protected void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage(), e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "The request was not valid: " + e.getMessage());
    }

    @ExceptionHandler
    protected void handleEntityNotFoundException(EntityNotFoundException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage(), e);
        response.sendError(HttpStatus.NOT_FOUND.value(), "The requested entity was not found");
    }

    @ExceptionHandler
    protected void handleEntityExistsException(EntityExistsException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "The indicated entity already exists");
    }

    @ExceptionHandler
    protected void handleStorageException(OrderServiceException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage(), e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Please seek assistance from an application maintainer");
    }

    protected PageRequest getPageRequest(Map<String, String> queryParams) {
        if (queryParams.containsKey(QueryParam.PAGE.toString())) {
            PageRequest pageRequest;
            Sort.Direction direction = getSortDirection(queryParams);
            if (direction == null) {
                pageRequest = PageRequest.of(Integer.valueOf(queryParams.get(QueryParam.PAGE.toString())) - 1, pageSize);
            } else {
                pageRequest = PageRequest.of(Integer.valueOf(queryParams.get(QueryParam.PAGE.toString())) - 1, pageSize, direction, "registrationNumber");
            }
            return pageRequest;
        } else {
            return PageRequest.of(0, pageSize);
        }
    }

    private Sort.Direction getSortDirection(Map<String, String> queryParams) {
        if (queryParams.containsKey(QueryParam.SORT.toString())) {
            return Sort.Direction.fromString(queryParams.get(QueryParam.SORT.toString()));
        } else {
            return null;
        }
    }

}
