package insper.com.br.simulado.Player;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Getter
@Setter
public class Player {
    @Id
    private String id;

    private String name;
    private Integer age;
    private ArrayList<String> teams;
}
