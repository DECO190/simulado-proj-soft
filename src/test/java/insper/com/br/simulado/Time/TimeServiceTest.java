package insper.com.br.simulado.Time;

import insper.com.br.simulado.Player.DTOs.UpdatePlayerDTO;
import insper.com.br.simulado.Player.Player;
import insper.com.br.simulado.Player.PlayerRepository;
import insper.com.br.simulado.Player.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void testListarJogadores() {
        Mockito.when(playerRepository.findAll()).thenReturn(new ArrayList<Player>());

        List<Player> players = playerService.getPlayers();

        Assertions.assertTrue(players.isEmpty());
    }

    @Test
    public void testCriarJogadorComTimeInvalido() {
        ArrayList<String> teams = new ArrayList<>();
        teams.add("10000");

        Player addPlayer = new Player();
        addPlayer.setAge(100);
        addPlayer.setName("Andre");
        addPlayer.setTeams(teams);

        Assertions.assertThrows(ResponseStatusException.class, () -> playerService.createPlayer(addPlayer));
    }

    @Test
    public void testCriarJogadorComTimeValido() {
        ArrayList<String> teams = new ArrayList<>();
        teams.add("1");

        Player addPlayer = new Player();
        addPlayer.setAge(100);
        addPlayer.setName("Andre");
        addPlayer.setTeams(teams);

        Mockito.when(playerRepository.save(addPlayer)).thenReturn(addPlayer);

        Player player = playerService.createPlayer(addPlayer);

        Assertions.assertEquals(addPlayer, player);
    }

    @Test
    public void alterarTimeJogadorComTimeInvalido() {
        UpdatePlayerDTO updatePlayerDTO = new UpdatePlayerDTO();
        updatePlayerDTO.setTeamName("10000");

        Assertions.assertThrows(ResponseStatusException.class, () -> playerService.updatePlayerTeam("1", updatePlayerDTO));
    }

    @Test
    public void adicionarTimeJogadorComTimeValido() {
        UpdatePlayerDTO updatePlayerDTO = new UpdatePlayerDTO();
        updatePlayerDTO.setTeamName("1");

        Player player = new Player();
        player.setTeams(new ArrayList<>());

        Mockito.when(playerRepository.findById("1")).thenReturn(java.util.Optional.of(player));

        Mockito.when(playerRepository.save(Mockito.any(Player.class))).thenReturn(new Player());

        playerService.updatePlayerTeam("1", updatePlayerDTO);

        Mockito.verify(playerRepository, Mockito.times(1)).save(Mockito.any(Player.class));
    }

}
