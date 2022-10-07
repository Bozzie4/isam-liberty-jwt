package dev.microprofile.jwt;

import java.security.Principal;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

// CLAIM
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.*;
import jakarta.json.JsonArray;

@RequestScoped
@Path("claims")
public class ClaimsEndpoint {
    @Inject
    private JsonWebToken callerPrincipal;

    @Inject
    @Claim("iss")
    private String issuer;

    @Inject
    @Claim("AZN_CRED_BROWSER_INFO")
    private String claimBrowserInfo;

    @Inject
    @Claim("AZN_CRED_REGISTRY_ID")
    private String claimRegistryId;

    @GET
    @Path("test")

    public String getTest() {
        return "<h1>test</h1>issuer: " + issuer + "\n<br/>browser:" + claimBrowserInfo + "\n<br/>registry id:" + claimRegistryId;
    }
    @GET
    @RolesAllowed("Echoer")

    public String echoInput(@Context SecurityContext sec, @QueryParam("input") String input) {
        Principal user = sec.getUserPrincipal();
        System.out.println("Issuer " + issuer);
        if (callerPrincipal == null) {
          return "user="+user.getName();
        }
        return "<h1>claims, requires echoer role</h1>user="+user.getName() + "\n" + callerPrincipal.getRawToken() + "\n<br/>claim: " + callerPrincipal.getClaim("AZN_CRED_PRINCIPAL_UUID");
    }
}