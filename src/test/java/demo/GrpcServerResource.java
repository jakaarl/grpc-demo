package demo;

import org.junit.rules.ExternalResource;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServerResource extends ExternalResource {
	
	public Server server;
		
	@Override
	public void before() throws Throwable {
		server = ServerBuilder.forPort(0)
				.addService(new HelloService())
				.build();
		server.start();
	}
	
	public void after() {
		try {
			server.shutdown();
			server.awaitTermination();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	

}
