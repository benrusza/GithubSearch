package com.brz.app

import com.brz.app.retrofit.Client
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class RetrofitTest {

    @Test
    fun testGetUsersSuccess() {
        val input = "benrusza"



        val response = Client.service.getUsers(input).execute()
        val body = response.body()
        assertEquals(200,response.code())
        assertTrue(body!!.items.size>=1)
        assertEquals("benrusza", body.items[0].login)
    }
}