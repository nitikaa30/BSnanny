package com.example.bsnanny.retrofit

import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.bookAppointment.BookRequest
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
import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.Data
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.reject.RejectResponse


interface ApiHelper {
    suspend fun checkUser( checkUserBody: CheckUserBody) : NetworkResults<CheckUserResponse>

    suspend fun authenticate(authenticationBody: AuthenticationBody) : NetworkResults<AuthenticationResponse>

    suspend fun saveFeedback(feedback: FeedbackBody):NetworkResults<FeedbackResponse>

    suspend fun getFeedbackList():NetworkResults<FeedbackListResponse>

    suspend fun getParentRequests():NetworkResults<ParentRequestsResponse>

    suspend fun acceptParentRequest(id: Data):NetworkResults<AcceptResponse>

    suspend fun rejectParentRequest(id: Data):NetworkResults<RejectResponse>

    suspend fun applytoFamilies(apply: BookRequest):NetworkResults<BookAppointmentResponse>

    suspend fun findNanny(findNannyBody: FindNannyBody) : NetworkResults<FindNannyResponse>

    suspend fun bookingStatus() : NetworkResults<PendingBookingResponse>

    suspend fun getBookingHistory() : NetworkResults<ParentBookingResponse>

    suspend fun cancelParentBookings(cancelBookingBody: CancelBookingBody) : NetworkResults<CancelBookingResponse>

    suspend fun getNannyDetails(userId : String) : NetworkResults<GetNannyResponse>

    suspend fun inviteNanny(inviteNannyBody: InviteNannyBody) : NetworkResults<InviteNannyResponse>
}