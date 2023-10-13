package sistemaCaptura.telasSistema;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sistemaCaptura.HistConsmRecurso;
import sistemaCaptura.conexao.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaMonitorDeRecursos {

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    public void criarTela(){


        JFrame frame = new JFrame("Monitor de Recursos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Consumo Disco");
        tableModel.addColumn("Consumo RAM");
        tableModel.addColumn("Consumo CPU (%)");
        tableModel.addColumn("Temperatura");
        tableModel.addColumn("Data hora");
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        JButton refreshButton = new JButton("Atualizar");
        panel.add(refreshButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Consulte e exiba os dados da tabela ao clicar no botão "Atualizar"
                List<HistConsmRecurso> dadosDoBanco = con.query("SELECT id, consumoDisco, consumoRam,consumoCpu,temperatura, dataHora FROM historicos",
                        new BeanPropertyRowMapper<>(HistConsmRecurso.class));

                System.out.println("Número de linhas retornadas: " + dadosDoBanco.size()); // Mensagem de depuração

                tableModel.setRowCount(0); // Limpa a tabela antes de atualizar
                for (HistConsmRecurso dado : dadosDoBanco) {
                    System.out.println("ID: " + dado.getId() + ", Consumo disco: " + dado.getConsumoDisco() + "Consumo Ram: " + dado.getConsumoRam() + ", Consumo Cpu: " + dado.getConsumoCpu() + ", Temperatura: " + dado.getTemperatura() + ", Data e Hora: " + dado.getDataHora()); // Mensagem de depuração
                    tableModel.addRow(new Object[]{dado.getId(),dado.getConsumoDisco(),dado.getConsumoRam(),dado.getConsumoCpu(),dado.getTemperatura(),dado.getDataHora()});
                }
            }
        });

        frame.setVisible(true);

    }

}
