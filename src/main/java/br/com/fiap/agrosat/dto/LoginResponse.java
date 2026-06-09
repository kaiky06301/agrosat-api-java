package br.com.fiap.agrosat.dto;

public record LoginResponse(
        String token,
        String tipo,
        String email,
        Long idUsuario,
        String nome,
        long expiraEm
) {
}
