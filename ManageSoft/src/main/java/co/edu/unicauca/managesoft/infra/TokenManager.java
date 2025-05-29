/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.managesoft.infra;

public class TokenManager {
    private static String token;

    // Establecer el token de autenticación
    public static void setToken(String newToken) {
        token = newToken;
    }

    // Obtener el token de autenticación
    public static String getToken() {
        return token;
    }
}
