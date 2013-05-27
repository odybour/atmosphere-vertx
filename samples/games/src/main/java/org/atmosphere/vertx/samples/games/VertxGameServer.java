/*
 * Copyright 2012 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.vertx.samples.games;

import org.atmosphere.vertx.VertxAtmosphere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.deploy.Verticle;


/**
 * A bootstrap class that start Vertx and Snake!.
 */
public class VertxGameServer extends Verticle {

    private static final Logger logger = LoggerFactory.getLogger(VertxGameServer.class);

    @Override
    public void start() throws Exception {
        VertxAtmosphere.Builder b = new VertxAtmosphere.Builder();
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                String path = req.path;
                if (path.equals("/")) {
                    path = "/index.html";
                }

                logger.info("Servicing request {}", path);
                if (!req.path.startsWith("/snake")) {
                    req.response.sendFile("src/main/resources" + path);
                }
            }
        });

        b.resource(SnakeManagedService.class).httpServer(httpServer).url("/snake").build();

        httpServer.listen(8080);
    }
}
