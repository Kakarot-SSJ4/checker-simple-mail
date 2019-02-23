package br.com.caelum.vraptor.simplemail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class ExecutorServiceProvider implements ComponentFactory<ExecutorService> {
	@SuppressWarnings("initialization")/* pool initialized in the function initialize() and not the constructor.
	Also, otherwise close() function would try to run shutdown() on a null object. */
	private ExecutorService pool;

	@PostConstruct
	public void initialize() {
		this.pool = Executors.newCachedThreadPool();
	}

	@Override
	public ExecutorService getInstance() {
		return this.pool;
	}

	@PreDestroy
	public void close() {
		this.pool.shutdown();
	}

}
