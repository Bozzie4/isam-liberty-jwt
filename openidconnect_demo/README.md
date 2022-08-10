---
# Source

This is based on this blog by Shane Weeden:

https://community.ibm.com/community/user/security/blogs/shane-weeden1/2016/11/11/isam-902-the-jwt-sts-module-and-junction-sso-to-we

# Maven build

Nothing to build here.

# Build & run Docker image

    cd openidconnect/
    sudo podman build -t openid:v0.0.1 .
    sudo podman run -d --replace --name openid -p 9081:9081 -p 9444:9444 openid:v0.0.1
    sudo podman logs -f openid

# Application paths

The application runs on the root context root.

- /dumpHeaders.jsp – Any user can access this, authenticated or unauthenticated. It dumps a HTML page of the HTTP headers received.
- /dump.jsp – Requires the “all” role. Dumps the contents of the JAAS Subject. Any jwt-header authenticated user should be able to access this.
- /whoami.jsp – Requires the “all” role. Dumps a simplified view of the JAAS Subject Principal.
- /rolea/dump.jsp – Requires the “RoleA” role, content is identical to /dump.jsp.
- /rolea/whoami.jsp – Requires the “RoleA” role, content is identical to /whoami.jsp.
- /roleb/dump.jsp – Requires the “RoleB” role, content is identical to /dump.jsp.
- /roleb/whoami.jsp – Requires the “RoleB” role, content is identical to /whoami.jsp.


There's 2 groups defined in the EAR, mapped to the RoleA and RoleB roles  
- groupa
- groupb

# WebSeal

```properties
[jwt:/openidtest]
key-label = jwtsign
claim = text::https://issuer::iss
claim = attr::AZN_CRED_PRINCIPAL_NAME::sub
claim = attr::AZN_CRED_PRINCIPAL_NAME::upn
claim = attr::AZN_*
claim = text::[Echoer]::roles
claim = text::[groupa,groupb]::groups
include-empty-claims = false
hdr-name = jwt
hdr-format = %TOKEN%
lifetime = 0
renewal-window = 15
```