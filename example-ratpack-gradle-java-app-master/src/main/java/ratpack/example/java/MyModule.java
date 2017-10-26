package ratpack.example.java;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import ratpack.handling.HandlerDecorator;

public class MyModule extends AbstractModule {
	
// A method for Binding the dependencies
	
  protected void configure() {
	bind(SmartAppPostHandler.class);
	bind(SmartAppGetHandler.class);
	bind(PayLoad.class);
    Multibinder.newSetBinder(binder(), HandlerDecorator.class).addBinding().toInstance(HandlerDecorator.prepend(new LoggingHandler()));
  }
}
