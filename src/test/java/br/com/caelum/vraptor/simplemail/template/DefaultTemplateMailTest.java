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
	@SuppressWarnings("initialization") // this class is a test class of DefaultTemplateMail, this will be customized when needed to check validity of the code
	private Localization localization;
	@Mock
	@SuppressWarnings("initialization") // this class is a test class of DefaultTemplateMail, this will be customized when needed to check validity of the code
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
	@SuppressWarnings("null") // e.getCause() != null due to the usage of fail() in the try block
	public void should_throw_exception_if_there_is_no_subject_at_messages_properties(){
		when(localization.getMessage(templateName, null)).thenReturn("???"+templateName+"???");
		try {
			templateMail.prepareEmail("leo", "leo@leo.com");
			fail();		
		}catch (RuntimeException e) {
			assertTrue(e.getCause().getClass().isAssignableFrom(IllegalArgumentException.class));
		}
	}
	
	@Test
	public void should_not_throw_exception_if_there_is_a_subject_at_messages_properties() throws IOException, TemplateException {
		when(localization.getMessage(templateName, null)).thenReturn("real template title");
		
		DefaultTemplateMail templateMail = new DefaultTemplateMail(templateName, template, localization, null, null);
		
		templateMail.prepareEmail("leo", "leo@leo.com");
	}

}
