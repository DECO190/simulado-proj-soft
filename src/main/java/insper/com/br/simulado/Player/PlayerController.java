package insper.com.br.simulado.Player;

import insper.com.br.simulado.Player.DTOs.UpdatePlayerDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PutMapping("/players/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePlayer(@PathVariable String id, @RequestBody UpdatePlayerDTO updatePlayerDTO) {
        playerService.updatePlayerTeam(id, updatePlayerDTO);

        return;
    }

}
