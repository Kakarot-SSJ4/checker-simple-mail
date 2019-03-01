package br.com.caelum.vraptor.simplemail.template;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.freemarker.Template;
import freemarker.template.TemplateException;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTemplateMailTest {

	@Mock
	@SuppressWarnings("initialization") // part of the library br.com.caelum.vraptor.core.Localization
	private Localization localization;
	@Mock
	@SuppressWarnings("initialization") // part of the library br.com.caelum.vraptor.freemarker.Template
	private Template template;
	@SuppressWarnings("initialization") // initialized in setUp()	
	private String templateName;
	@SuppressWarnings("initialization") // initialized in setUp()
	private DefaultTemplateMail templateMail;

	@Before
	public void setUp() throws IOException, TemplateException{
		when(template.getContent()).thenReturn("Some message");
		templateName = "templateNameKey";
		templateMail = new DefaultTemplateMail(templateName, template, localization, null, null);
	}

	@Test
	@SuppressWarnings("null") /*e will not be null when assertTrue(e.getCause().getClass().isAssignableFrom(IllegalArgumentException.class)); 
	is called because it is inside the catch statement
	*/
	public void should_throw_exception_if_there_is_no_subject_at_messages_properties(){
		when(localization.getMessage(templateName, null)).thenReturn("???"+templateName+"???");
		
		try {
			templateMail.prepareEmail("leo", "leo@leo.com");
			fail();		
		} catch (RuntimeException e) {
			assertTrue(e.getCause()==null?false:e.getCause().getClass().isAssignableFrom(IllegalArgumentException.class));
			// if e.getCause is null, no further data can be fetched hence,isAssignableFrom(IllegalArgumentException.class) will be false
		}
	}
	
	@Test
	public void should_not_throw_exception_if_there_is_a_subject_at_messages_properties() throws IOException, TemplateException {
		when(localization.getMessage(templateName, null)).thenReturn("real template title");
		
		DefaultTemplateMail templateMail = new DefaultTemplateMail(templateName, template, localization, null, null);
		
		templateMail.prepareEmail("leo", "leo@leo.com");
	}

}
