package com.podolab.api.reservation;

import com.podolab.api.global.exception.BaseException;
import com.podolab.api.global.exception.ErrorCode;
import com.podolab.api.seat.Seat;
import com.podolab.api.seat.SeatRepository;
import com.podolab.api.seathold.SeatHold;
import com.podolab.api.seathold.SeatHoldRepository;
import com.podolab.api.ticket.Ticket;
import com.podolab.api.ticket.TicketRepository;
import com.podolab.api.user.User;
import com.podolab.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final SeatHoldRepository seatHoldRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Long hold(Long userId, Long seatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new BaseException(ErrorCode.SEAT_NOT_FOUND));

        seat.reserve();

        SeatHold seatHold = SeatHold.create(user, seat, LocalDateTime.now().plusMinutes(5));
        return seatHoldRepository.save(seatHold).getId();
    }

    @Transactional
    public void release(Long seatHoldId) {
        SeatHold seatHold = seatHoldRepository.findById(seatHoldId)
                .orElseThrow(() -> new BaseException(ErrorCode.HOLD_NOT_FOUND));

        seatHold.release();
    }

    @Transactional
    public void confirm(Long seatHoldId, Long userId) {
        SeatHold seatHold = seatHoldRepository.findById(seatHoldId)
                .orElseThrow(() -> new BaseException(ErrorCode.HOLD_NOT_FOUND));

        if (!seatHold.getUser().getId().equals(userId)) {
            throw new BaseException(ErrorCode.HOLD_USER_MISMATCH);
        }

        seatHold.confirm();

        ticketRepository.save(Ticket.create(seatHold.getUser(), seatHold.getSeat(), seatHold.getConcert()));
    }
}