package com.app.babybaby.repository.board.ask;

import com.app.babybaby.entity.board.ask.Ask;
import com.app.babybaby.entity.board.ask.QAsk;
import com.app.babybaby.entity.board.event.Event;
import com.app.babybaby.search.admin.AdminAskSearch;
import com.app.babybaby.search.board.parentsBoard.EventBoardSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.app.babybaby.entity.board.ask.QAsk.ask;
import static com.app.babybaby.entity.board.event.QEvent.event;

@RequiredArgsConstructor
public class AskQueryDslImpl implements AskQueryDsl {

    private final JPAQueryFactory query;

    //  [관리자] 문의 목록 조회
    @Override
    public Page<Ask> findAllAsk_queryDSL(Pageable pageable, AdminAskSearch adminAskSearch) {
        BooleanExpression askTitleEq = adminAskSearch.getAskTitle() == null ? null : ask.boardTitle.eq(adminAskSearch.getAskTitle());

        QAsk ask = QAsk.ask;
        List<Ask> foundAsk = query.select(ask)
                .from(ask)
                .where(askTitleEq)
                .orderBy(ask.id.asc())
                .offset(pageable.getOffset() - 1)
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(ask.count())
                .from(ask)
                .where(askTitleEq)
                .fetchOne();

        return new PageImpl<>(foundAsk, pageable, count);
    }

    //  [관리자] 문의 상세보기
    @Override
    public Optional<Ask> findAskById_queryDSL(Long askId) {
        return Optional.ofNullable(
                query.select(ask)
                        .from(ask)
                        .where(ask.id.eq(askId))
                        .fetchOne());
    }

    //  [관리자] 문의 삭제하기
    @Override
    public void deleteAskByIds_queryDSL(List<Long> askIds) {
        query.delete(ask)
                .where(ask.id.in(askIds))
                .execute();
    }

    
//    내가쓴 문의 목록
    @Override
    public Slice<Ask> findAllAskByMemberId(Long memberId, Pageable pageable, AdminAskSearch adminAskSearch) {
        BooleanExpression askTitleContains = adminAskSearch.getAskTitle() == null ? null : ask.boardTitle.contains(adminAskSearch.getAskTitle());

        List<Ask> asks = query.select(ask)
                .from(ask)
                .leftJoin(ask.askAnswer).fetchJoin()
                .where(ask.member.id.eq(memberId),askTitleContains)
                .orderBy(ask.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        boolean hasNext = false;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (asks.size() > pageable.getPageSize()) {
            hasNext = true;
            asks.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(asks, pageable, hasNext);
    }








}
