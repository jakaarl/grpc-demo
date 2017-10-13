package demo;

import demo.HelloRequestOuterClass.HelloRequest;
import demo.HelloResponseOuterClass.HelloResponse;
import demo.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;

public class HelloService extends HelloServiceImplBase {
	
	public static final String GREETING_FORMAT = "Hello %1$s";
	
	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		responseObserver.onNext(getResponse(request));
		responseObserver.onCompleted();
	}
	
	private HelloResponse getResponse(HelloRequest request) {
		return HelloResponse.newBuilder().setGreeting(
				String.format(GREETING_FORMAT, request.getName())).build();
	}

}
