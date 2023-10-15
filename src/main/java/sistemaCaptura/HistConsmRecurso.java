package sistemaCaptura;

import com.github.britooo.looca.api.core.Looca;
import org.springframework.jdbc.core.JdbcTemplate;
import sistemaCaptura.conexao.Conexao;
import sistemaCaptura.telasSistema.TelaMonitorDeRecursos;


import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class HistConsmRecurso {

    private Integer id;
    private String sistemaOperacional;
    private Double consumoCpu;
    private Double consumoDisco;
    private Double consumoRam;
    private Double totalCpu;
    private Double totalDisco;
    private Double totalRam;
    private LocalDateTime dataHora = LocalDateTime.now();

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    Looca looca = new Looca();
    Timer timer = new Timer();
    TelaMonitorDeRecursos telaMonitorDeRecursos = new TelaMonitorDeRecursos();

    public HistConsmRecurso() {
    }

    public HistConsmRecurso(Integer id,String sistemaOperacional , Double consumoCpu, Double consumoDisco, Double consumoRam, Double totalCpu, Double totalDisco, Double totalRam, LocalDateTime dataHora) {
        this.id = id;
        this.sistemaOperacional= sistemaOperacional;
        this.consumoCpu = consumoCpu;
        this.consumoDisco = consumoDisco;
        this.consumoRam = consumoRam;
        this.totalCpu = totalCpu;
        this.totalDisco = totalDisco;
        this.totalRam = totalRam;
        this.dataHora = dataHora;

    }

    public void mostrarHistorico() {

        telaMonitorDeRecursos.criarTela();
        insertHistorico();
    }

    public void insertHistorico() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String sistemasOperacional = (looca.getSistema()).getSistemaOperacional();

                Double consumoCpu = (looca.getProcessador().getUso()).doubleValue();
//                Double porcentagemUsoCpu = (consumoCpu / totalCpu) * 100.0;
                Double totalCpu = (looca.getProcessador().getFrequencia()).doubleValue();

                Double consumoDisco = (double)((looca.getGrupoDeDiscos().getTamanhoTotal() / 100000) / looca.getGrupoDeDiscos().getQuantidadeDeDiscos());
//                Double consumoDisco = (looca.getGrupoDeDiscos().getTamanhoTotal()).doubleValue();
                Double totalDisco = (looca.getGrupoDeDiscos().getTamanhoTotal()).doubleValue();

                Double consumoRam = (looca.getMemoria().getEmUso()).doubleValue();
                Double totalRam = (looca.getMemoria().getTotal()).doubleValue();
//                double porcentagemUsoRam = (consumoRam / totalRam) * 100.0;

                dataHora = LocalDateTime.now();

                con.update("INSERT INTO hardwares (id,sistemaOperacional,consumoCpu, consumoDisco, consumoRam, totalCpu, totalDisco, totalRam, dataHora) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)",
                        id,sistemasOperacional,consumoCpu, consumoDisco, consumoRam, totalCpu, totalDisco, totalRam, dataHora);
                System.out.println("Inserindo dados...");
            }
        }, 1000, 1000);
    }

    // Outros métodos getter e setter...


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Double getConsumoCpu() {
        return consumoCpu;
    }

    public void setConsumoCpu(Double consumoCpu) {
        this.consumoCpu = consumoCpu;
    }

    public Double getConsumoDisco() {
        return consumoDisco;
    }

    public void setConsumoDisco(Double consumoDisco) {
        this.consumoDisco = consumoDisco;
    }

    public Double getConsumoRam() {
        return consumoRam;
    }

    public void setConsumoRam(Double consumoRam) {
        this.consumoRam = consumoRam;
    }

    public Double getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(Double totalCpu) {
        this.totalCpu = totalCpu;
    }

    public Double getTotalDisco() {
        return totalDisco;
    }

    public void setTotalDisco(Double totalDisco) {
        this.totalDisco = totalDisco;
    }

    public Double getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(Double totalRam) {
        this.totalRam = totalRam;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "HistConsmRecurso{" +
                "id=" + id +
                "Sistema Operacional=" + sistemaOperacional +
                ", consumoCpu=" + consumoCpu +
                ", consumoDisco=" + consumoDisco +
                ", consumoRam=" + consumoRam +
                ", totalCpu=" + totalCpu +
                ", totalDisco=" + totalDisco +
                ", totalRam=" + totalRam +
                ", dataHora=" + dataHora +
                '}';
    }
}
