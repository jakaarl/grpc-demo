package demo;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

import demo.HelloRequestOuterClass.HelloRequest;
import demo.HelloResponseOuterClass.HelloResponse;
import demo.HelloServiceGrpc.HelloServiceBlockingStub;
import demo.HelloServiceGrpc.HelloServiceFutureStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloServiceTest {
	
	@ClassRule
	public static final GrpcServerResource SERVER_RESOURCE = new GrpcServerResource();
	
	private static final String NAME_ARG = "World";
	private static final String EXPECTED_GREETING = String.format(HelloService.GREETING_FORMAT, NAME_ARG);
	
	@Test
	public void blockingCallSucceeds() {
		HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(createChannel());
		HelloResponse response = stub.sayHello(HelloRequest.newBuilder().setName(NAME_ARG).build());
		assertEquals(EXPECTED_GREETING, response.getGreeting());
	}
	
	@Test
	public void asyncCallSucceeds() throws InterruptedException, ExecutionException {
		HelloServiceFutureStub stub = HelloServiceGrpc.newFutureStub(createChannel());
		ListenableFuture<HelloResponse> future = stub.sayHello(HelloRequest.newBuilder().setName(NAME_ARG).build());
		HelloResponse response = future.get();
		assertEquals(EXPECTED_GREETING, response.getGreeting());
	}
	
	private ManagedChannel createChannel() {
		return ManagedChannelBuilder.forAddress(
				"localhost", SERVER_RESOURCE.server.getPort()).usePlaintext(true).build();
	}

}
