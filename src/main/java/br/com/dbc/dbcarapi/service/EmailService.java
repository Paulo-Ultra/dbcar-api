package br.com.dbc.dbcarapi.service;


import br.com.dbc.dbcarapi.dto.ClienteDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fnConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmailCliente(ClienteDTO clienteDTO, String tipo) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(clienteDTO.getEmail());
            if (tipo.equalsIgnoreCase("alugado")) {
                mimeMessageHelper.setSubject("Parabéns! Você alugou o carro com sucesso!");
            }
            mimeMessageHelper.setText(getContentFromTemplatePessoa(clienteDTO, tipo), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplatePessoa(ClienteDTO clienteDTO, String tipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", clienteDTO.getNome());
        dados.put("id", clienteDTO.getIdCliente());
        dados.put("email", from);

        Template template = null;
        if (tipo.equalsIgnoreCase("alugado")) {
            template = fnConfiguration.getTemplate("emailAluguelCarro-template.ftl");
        }

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}