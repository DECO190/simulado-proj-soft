package insper.com.br.simulado.Player;

import insper.com.br.simulado.Player.DTOs.UpdatePlayerDTO;
import insper.com.br.simulado.Time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    private Time getTeam(String identifier) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Time> response = restTemplate.getForEntity("http://campeonato:8080/time/" + identifier, Time.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível consultar o time - " + identifier);
            }

            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível consultar o time - " + identifier);
        }
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player createPlayer(Player player) {
        for (String teamName : player.getTeams()) {
            Time tempTeam = getTeam(teamName);

            if (tempTeam == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o time do jogador - " + teamName);
            }
        }

        return playerRepository.save(player);
    }

    public void updatePlayerTeam(String id, UpdatePlayerDTO updatePlayerDTO) {
        Optional<Player> playerQuery = playerRepository.findById(id);

        if (playerQuery.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível o jogador");
        }

        if (updatePlayerDTO.getTeamName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do time invalido");
        }

        Time playerTeam = getTeam(updatePlayerDTO.getTeamName());

        if (playerTeam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível encontrar o time do jogador");
        }

        Player player = playerQuery.get();

        List<String> playerTeams = player.getTeams();

        player.getTeams().add(updatePlayerDTO.getTeamName());

        playerRepository.save(player);
    }



}
