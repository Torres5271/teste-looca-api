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
    private Integer consumoCpu;
    private Integer consumoDisco;
    private Integer consumoRam;
    private LocalDateTime dataHora = LocalDateTime.now();
    private  Double temperatura;

    // Imprimir a data e hora atual
//        System.out.println("Data e Hora Atual: " + dataHoraAtual);


    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    Looca looca = new Looca();
    Timer timer = new Timer();
    TelaMonitorDeRecursos telaMonitorDeRecursos = new TelaMonitorDeRecursos();


    public HistConsmRecurso() {
    }

    public HistConsmRecurso(Integer id, Integer consumoCpu, Integer consumoDisco, Integer consumoRam, LocalDateTime dataHora,Double temperatura) {
        this.id = id;
        this.consumoCpu = consumoCpu;
        this.consumoDisco = consumoDisco;
        this.consumoRam = consumoRam;
        this.dataHora = dataHora;
        this.temperatura = temperatura;
    }

    public void mostrarHistorico() {
        con.execute("DROP TABLE IF EXISTS historicos ");
        con.execute("""
                    create table if not exists historicos(
                      id int primary key auto_increment,
                      consumoDisco double not null,
                      consumoRam double not null,
                      consumoCpu double not null,
                      temperatura double not null,
                      dataHora datetime,
                      fkHardware int,
                      foreign key (fkHardware) references hardwares(idHardware)
                      );
                """);

        telaMonitorDeRecursos.criarTela();
        insertHistorico();

    }

    public void insertHistorico() {


        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Integer consumoCpu = (looca.getProcessador().getUso()).intValue();
                Long consumoDisco = (looca.getGrupoDeDiscos().getTamanhoTotal() / 100000) / looca.getGrupoDeDiscos().getQuantidadeDeDiscos();
                Long consumoRam = (long) (looca.getMemoria().getEmUso()).intValue();
                dataHora = LocalDateTime.now();
                Double temperatura= (looca.getTemperatura()).getTemperatura();

                con.update("INSERT INTO historicos (id,consumoDisco,consumoRam,consumoCpu,temperatura,dataHora)VALUES (?,?, ?, ?, ?, ?)", id,consumoDisco, consumoRam,consumoCpu,temperatura, dataHora);
                System.out.println("Inserindo dados...");

            }

        }, 1000, 1000);

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsumoCpu() {
        return consumoCpu;
    }

    public void setConsumoCpu(Integer consumoCpu) {
        this.consumoCpu = consumoCpu;
    }

    public Integer getConsumoDisco() {
        return consumoDisco;
    }

    public void setConsumoDisco(Integer consumoDisco) {
        this.consumoDisco = consumoDisco;
    }

    public Integer getConsumoRam() {
        return consumoRam;
    }

    public void setConsumoRam(Integer consumoRam) {
        this.consumoRam = consumoRam;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "HistConsmRecurso{" +
                ", id" + id +
                ", consumoCpu=" + consumoCpu +
                ", consumoDisco=" + consumoDisco +
                ", consumoRam=" + consumoRam +
                ", temperatura=" + temperatura +
                ", dataHora=" + dataHora +
                '}';
    }
}
