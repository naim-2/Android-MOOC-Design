// This is a Unit Test

package com.example.mooc1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CountrySearchUnitTest {

    @Mock
    HttpURLConnection urlConnection;

    private CountrySearch.NetworkTask networkTask;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        networkTask = new CountrySearch().new NetworkTask();
    }

    @Test
    public void testDoInBackground_Success() throws Exception {
        // Mocked JSON response
        String mockResponse = "{\"name\":{\"common\":\"United States\",\"official\":\"United States of America\"}}";

        // Mocked InputStream
        InputStream inputStream = new ByteArrayInputStream(mockResponse.getBytes());

        // Stubbing HttpURLConnection behavior
        when(urlConnection.getInputStream()).thenReturn(inputStream);

        // Stubbing URL behavior
        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(urlConnection);

        // Execute the method under test
        String result = networkTask.doInBackground(mockUrl.toString(), "United States");

        // Verify that the result is as expected
        assertEquals(mockResponse, result);

        // Verify that URL connection methods were called
        verify(urlConnection).disconnect();
        verify(urlConnection).getInputStream();
    }

    @Test
    public void testDoInBackground_Error() throws Exception {
        // Stubbing URL behavior
        URL mockUrl = mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(urlConnection);

        // Stubbing HttpURLConnection behavior to throw IOException
        when(urlConnection.getInputStream()).thenThrow(new IOException());

        // Execute the method under test
        String result = networkTask.doInBackground(mockUrl.toString(), "Invalid Country");

        // Verify that an empty string is returned when an error occurs
        assertEquals("", result);

        // Verify that URL connection methods were called
        verify(urlConnection).disconnect();
        verify(urlConnection).getInputStream();
    }
}
