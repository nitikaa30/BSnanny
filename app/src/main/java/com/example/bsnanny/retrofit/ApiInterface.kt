package com.example.bsnanny.retrofit

import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.bookAppointment.BookRequest
import com.example.bsnanny.models.bookings.BookingsResponse
import com.example.bsnanny.models.bookings.parentBooking.ParentBookingResponse
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingBody
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingResponse
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.models.findNanny.FindNannyBody
import com.example.bsnanny.models.findNanny.FindNannyResponse
import com.example.bsnanny.models.findNanny.getNanny.GetNannyResponse
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyBody
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyResponse
import com.example.bsnanny.models.pendingBookings.PendingBookingResponse
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.reject.RejectResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("user/find_number")
    suspend fun checkUser(@Body checkUserBody: CheckUserBody): Response<CheckUserResponse>

    @POST("user/authenticate")
    suspend fun authenticate(@Body authenticationBody: AuthenticationBody) : Response<AuthenticationResponse>
    @POST("feedback/save")
    suspend fun saveFeedback(@Body feedback: FeedbackBody): Response<FeedbackResponse>

    @GET("feedback/list_feedbacks")
    suspend fun getFeedbackList():Response<FeedbackListResponse>

    @GET("nany/get_parent_requests")
    suspend fun getParentRequests():Response<ParentRequestsResponse>

    @POST("nany/accept")
    suspend fun acceptParentRequest(id:Int): Response<AcceptResponse>

    @POST("nany/reject")
    suspend fun rejectParentRequest(id:Int):Response<RejectResponse>

    @POST("nany/apply")
    suspend fun applytoFamiliy(@Body apply: BookRequest):Response<BookAppointmentResponse>

    @GET("nany/view-parent-booking?booking_id=booking_id")
    suspend fun viewBooking():Response<BookingsResponse>

    @POST("parent/find_nany")
    suspend fun findNanny(@Body findNannyBody: FindNannyBody) : Response<FindNannyResponse>

    @GET("parent/is_booking_pending")
    suspend fun getBookingStatus() : Response<PendingBookingResponse>

    @GET("user/booking_history")
    suspend fun getBookingHistory() : Response<ParentBookingResponse>


    @POST("parent/cancel_booking")
    suspend fun cancelBookingParent(@Body cancelBookingBody: CancelBookingBody) : Response<CancelBookingResponse>


    @GET("parent/getnany")
    suspend fun getNannyDetails(@Query("id") userId : String) : Response<GetNannyResponse>

    @POST("parent/invite_nany")
    suspend fun inviteNanny(@Body inviteNannyBody: InviteNannyBody) : Response<InviteNannyResponse>

}