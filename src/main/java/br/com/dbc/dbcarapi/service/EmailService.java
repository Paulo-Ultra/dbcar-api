package br.com.dbc.dbcarapi.service;


import br.com.dbc.dbcarapi.dto.ClienteDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
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
@Slf4j
public class EmailService {

    private final freemarker.template.Configuration fnConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmailCliente(ClienteDTO clienteDTO) {
        log.info("Enviando e-mail de boas vindas para " + clienteDTO.getNome());
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(from);
        mensagem.setTo(clienteDTO.getEmail());
        mensagem.setSubject("Seja bem vindo a DBCAR!");
        mensagem.setText("Saudações " + clienteDTO.getNome() + "\n" + "Seu cadastro na nossa locadora foi realizado com sucesso! Seu identificador de cliente é: " + clienteDTO.getIdCliente() + "\n" + "Qual dúvida, reclamação ou sugestão entre em contato conosco pelo e-mail: suportedbcar@dbcar.com.br" + "\n" + "Atenciosamente, Equipe DBCAR.");
    }

    public void sendEmailAluguel(ClienteDTO clienteDTO) {
        log.info("Enviando e-mail de aluguel realizado...");
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(from);
        mensagem.setTo(clienteDTO.getEmail());
        mensagem.setSubject("Parabéns, seu aluguel foi realizado com sucesso!");
        mensagem.setText("Parabéns " + clienteDTO.getNome() + "\n" + "Seu aluguel do veículo foi realizado com sucesso! Qual dúvida, reclamação ou sugestão entre em contato conosco pelo e-mail: suportedbcar@dbcar.com.br" + "\n" + "Atenciosamente, Equipe DBCAR.");
    }
}