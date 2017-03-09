/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.util.ErrorBean;
import controller.util.ValidationUtil;
import domain.Person;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.mvc.binding.BindingResult;
import javax.validation.Valid;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import service.facade.PersonFacade;

/**
 *
 * @author jGauravGupta
 */
@Path("person")
public class PersonController {

    @Inject
    private Models model;
    @Inject
    private PersonFacade facade;
    @Inject
    private BindingResult validationResult;
    @Inject
    private ErrorBean error;

    @GET
    @Path("new")
    @Controller
    public String emptyPerson() {
        return "person/create.jsp";
    }

    @POST
    @Path("new")
    @Controller
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String createPerson(@Valid
            @BeanParam Person person) {
        if (validationResult.isFailed()) {
            return ValidationUtil.getResponse(validationResult, error);
        }
        facade.create(person);
        return "redirect:person/list";
    }

    @GET
    @Path("update/{id}")
    @Controller
    public String editPerson(@PathParam("id") Long id) {
        model.put("PERSON", facade.find(id));
        return "person/update.jsp";
    }

    @POST
    @Path("update")
    @Controller
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String updatePerson(@Valid
            @BeanParam Person person) {
        if (validationResult.isFailed()) {
            return ValidationUtil.getResponse(validationResult, error);
        }
        facade.edit(person);
        return "redirect:person/list";
    }

    @GET
    @Path("remove/{id}")
    @Controller
    public String removePerson(@PathParam("id") Long id) {
        facade.remove(facade.find(id));
        return "redirect:person/list";
    }

    @GET
    @Path("{id}")
    @Controller
    public String findPerson(@PathParam("id") Long id) {
        model.put("PERSON", facade.find(id));
        return "person/view.jsp";
    }

    @GET
    @Path("list")
    @Controller
    public String findAllPerson() {
        model.put("PERSON_LIST", facade.findAll());
        return "person/list.jsp";
    }
    
}
