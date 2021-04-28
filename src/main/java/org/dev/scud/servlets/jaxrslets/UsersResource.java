package org.dev.scud.servlets.jaxrslets;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dev.scud.models.User;
import org.dev.scud.orm.interfaces.UserModelConnectorInterface;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Path("/users")
@RequestScoped
public class UsersResource {

    @Inject
    private UserModelConnectorInterface mc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(mc.getAllUsers());
            mc.disconnect();
            return Response.ok().entity(json).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{}").build();
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        try {
            Gson gson = new Gson();
            User user = mc.getUser(id);
            if (user == null)
                return Response.status(404).entity("{}").build();
            String json = gson.toJson(user);
            mc.disconnect();
            return Response.ok().entity(json).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{}").build();
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("{}").build();
        }
    }

    @PUT
    @Path("/add{name: (/name)?}")
    public Response addUser(@QueryParam("name") String name) {
        if (name == null)
            return Response.status(400).entity("{}").build();
        try {
            Gson gson = new Gson();
            User user = new User(UUID.randomUUID(), name);
            if (!mc.createUser(user))
                return Response.status(400).entity("{}").build();
            String json = gson.toJson(user);
            mc.disconnect();
            return Response.ok().entity(json).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{}").build();
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("{}").build();
        }
    }

    @POST
    @Path("/update{id: (/id)?}{name: (/name)?}")
    public Response updateUser(@QueryParam("id") String id, @QueryParam("name") String name) {
        if (id == null || name == null)
            return Response.status(400).entity("{}").build();
        try {
            Gson gson = new Gson();
            try {
                UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                return Response.status(400).entity("{}").build();
            }
            User user = new User(UUID.fromString(id), name);
            if (!mc.updateUser(user))
                return Response.status(400).entity("{}").build();
            String json = gson.toJson(user);
            mc.disconnect();
            return Response.ok().entity(json).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{}").build();
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("{}").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        if (id == null)
            return Response.status(400).entity("{}").build();
        try {
            try {
                UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                return Response.status(400).entity("{}").build();
            }
            if (!mc.deleteUser(id)) {
                return Response.status(404).entity("{}").build();
            }
            mc.disconnect();
            return Response.ok().entity("{}").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity("{}").build();
        } catch (IllegalAccessException e) {
            return Response.status(403).entity("{}").build();
        }
    }
}