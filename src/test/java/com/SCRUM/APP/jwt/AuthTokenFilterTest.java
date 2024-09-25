package com.SCRUM.APP.jwt;

import com.SCRUM.APP.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testFilter_WithValidToken() throws Exception {
        String token = "valid.token.here";
        String username = "username";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(((UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getPrincipal(), userDetails);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testFilter_WithInvalidToken() throws Exception {
        String token = "invalid.token.here";
        String username = "username";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtService, times(1)).isTokenValid(token, userDetails);
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testFilter_NoToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(jwtService, never()).getUsernameFromToken(any());
        verify(jwtService, never()).isTokenValid(any(), any());

        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testFilter_WithTokenButNoUsername() throws Exception {
        String token = "token.without.username";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(null);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(jwtService, times(1)).getUsernameFromToken(token);
        verify(jwtService, never()).isTokenValid(any(), any());

        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }
}
