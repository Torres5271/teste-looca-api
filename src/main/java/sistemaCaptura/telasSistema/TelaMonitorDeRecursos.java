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
        tableModel.addColumn("Sistema Opearional");
        tableModel.addColumn("Consumo Disco");
        tableModel.addColumn("Total Disco");
        tableModel.addColumn("Consumo RAM");
        tableModel.addColumn("Total RAM");
        tableModel.addColumn("Consumo CPU ");
        tableModel.addColumn("Total CPU");
        tableModel.addColumn("Data hora");
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        JButton refreshButton = new JButton("Atualizar Dados");
        panel.add(refreshButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);

        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();


        refreshButton.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                List<HistConsmRecurso> dadosDoBanco = con.query("SELECT id,sistemaOperacional, consumoCpu, consumoDisco, consumoRam, totalDisco, totalCpu, totalRam, dataHora FROM hardwares",
                        new BeanPropertyRowMapper<>(HistConsmRecurso.class));

                System.out.println("Número de linhas retornadas: " + dadosDoBanco.size());

                tableModel.setRowCount(0);
                for (HistConsmRecurso dado : dadosDoBanco) {
                    System.out.println("ID: " + dado.getId() + ", Consumo Disco: " + dado.getConsumoDisco() + ", Consumo RAM: " + dado.getConsumoRam() + ", Consumo CPU: " + dado.getConsumoCpu() + ", Total Disco: " + dado.getTotalDisco() + ", Total CPU: " + dado.getTotalCpu() + ", Total RAM: " + dado.getTotalRam() + ", Data e Hora: " + dado.getDataHora());
                    tableModel.addRow(new Object[]{dado.getId(),dado.getSistemaOperacional(), dado.getConsumoDisco()/1073741824.0,dado.getTotalDisco()/1073741824.0, dado.getConsumoRam()/1073741824.0,  dado.getTotalRam()/1073741824.0, dado.getConsumoCpu()/1073741824.0, dado.getTotalCpu()/1073741824.0, dado.getDataHora()});
                }
            }
        });

        frame.setVisible(true);
    }

}
