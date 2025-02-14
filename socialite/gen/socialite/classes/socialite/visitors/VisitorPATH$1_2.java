package socialite.visitors;
/* 
 
*/
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.TIntCollection;
import gnu.trove.TDoubleCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.iterator.TLongObjectIterator;

import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.collection.*;

import socialite.resource.*;
import socialite.tables.*;
import socialite.functions.Math;
import socialite.functions.*;
import socialite.visitors.*;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;

import socialite.resource.RuleMap;
import socialite.eval.EvalWithTable;
import socialite.dist.msg.WorkerMessage;

import socialite.eval.TmpTablePool;
import socialite.eval.Worker;
import socialite.eval.DeltaStepWindow;

import socialite.type.*;
import socialite.util.HashCode;
import socialite.util.Assert;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteFinishEval;
import socialite.util.UserLog;

import org.python.core.PyTuple;
import org.python.core.PyList;
import org.python.core.PySequenceList;
import socialite.tables.Edge_int_int_int_nest1_indexby0_range0to875713_groupby0_id0;
import socialite.tables.PATH$1_int_int_indexby0_range0to875713_groupby0_id2;


public final class VisitorPATH$1_2 extends VisitorImpl implements Runnable {
    private Edge_int_int_int_nest1_indexby0_range0to875713_groupby0_id0 $_edge0;
    private PATH$1_int_int_indexby0_range0to875713_groupby0_id2 $headTable;
    private SRuntime $runtime;
    private final int $epochId;
    private final int $ruleId;
    private TableInstRegistry $registry;
    private TableSliceMap $sliceMap;
    private LockMap $lockMap;
    private final int $const4;
    private int t;
    private int d;
    private int aggr$ret;
    private int $aggrVar;
    private int $currentPredicate;
    private int $firstTableIdx;
    private int $userlogCount;
    private Remote_PATH$1_2_int_int_groupby0_id2[] remoteTable_head=new Remote_PATH$1_2_int_int_groupby0_id2[2];
;
    public  VisitorPATH$1_2(int _$epochId, int _$ruleId, Integer _$$const4, Edge_int_int_int_nest1_indexby0_range0to875713_groupby0_id0 _$$_edge0, PATH$1_int_int_indexby0_range0to875713_groupby0_id2 $$headTable, SRuntime _$runtime, int _$firstTableIdx) {
        $const4=_$$const4;
        $_edge0 = _$$_edge0;
        $headTable=$$headTable;
        $epochId=_$epochId;
        $ruleId=_$ruleId;
        $runtime=_$runtime;
        $sliceMap = _$runtime.getSliceMap();
        $lockMap = _$runtime.getLockMap();
        $registry= _$runtime.getTableRegistry();
        $firstTableIdx = _$firstTableIdx;
        $currentPredicate=0;
    }

    public int getRuleId() {
        return $ruleId;
    }

    public int getEpochId() {
        return $epochId;
    }

    public void run() {
        do {
          $currentPredicate=0;
          $_edge0.iterate_by_0($const4, this);
        } while (false);;
        for(int _$i=0; _$i<remoteTable_head.length; _$i++) {
            TmpTableInst _$t=remoteTable_head[_$i];
            if (_$t!=null && _$t.size()>0) {
                SRuntime _$rt=SRuntimeWorker.getInst();
                RuleMap _$rm=_$rt.getRuleMap(getRuleId());
                int _$depRuleId=_$rm.getRemoteHeadDep(getRuleId());
                EvalWithTable _$cmd=new EvalWithTable($epochId, _$depRuleId,_$t,0);
                boolean _$reuse = _$rt.sender().send(_$i,_$cmd);
                if(!_$reuse)nullifyRemoteTable_head(_$i);
            };
        };
        TmpTablePool.free(worker.id(),remoteTable_head);
    }

    public boolean visit_0(int _0) {
        do {
          switch ($currentPredicate) {
              case 0: 
              {if ($const4!=_0) {
                  return false;
              };
              ;
              break;
              }

          };
        } while (false);;
        return true;
    }

    public boolean visit(int _0, int _1) {
        do {
          switch ($currentPredicate) {
              case 0: 
              {t=(int)_0;
              d=(int)_1;
              boolean $isUpdated=false;
              if ($sliceMap.isLocal(2,t)) {
                  {PATH$1_int_int_indexby0_range0to875713_groupby0_id2 _$$headTable=$headTable;
                  if (_$$headTable.contains(t)) {
                      int _$oldAns;
                      _$oldAns=_$$headTable.groupby(t);
                      aggr$ret=Builtin.min(_$oldAns, d) ;
                      if (_$oldAns!=0 && _$oldAns==aggr$ret) continue;
                  };
                  int $_slice_5=$sliceMap.getRangeIndex(2,0,t);
                  try {
                      $lockMap.lock(2, $_slice_5);
                      if (!_$$headTable.contains(t)) {
                          $aggrVar=d; ;
                          _$$headTable.insert(t, $aggrVar);
                          $isUpdated=true;
                      } else {
                          {int _$oldAns;
                          _$oldAns=_$$headTable.groupby(t);
                          $aggrVar=Builtin.min(_$oldAns, d) ;
                          if($aggrVar==(_$oldAns)) continue;
                          $isUpdated=_$$headTable.update(t, $aggrVar);};
                      };
                  } finally {
                      $lockMap.unlock(2, $_slice_5);
                  };
                  if ($isUpdated) {
                      {;};
                  };};
              } else {
                  Remote_PATH$1_2_int_int_groupby0_id2 _$remoteT;
                  int _$machineIdx=$sliceMap.machineIndexFor(2,t);
                  _$remoteT=getRemoteTable_head(t);
                  {_$remoteT.insert(t, d);};
                  {if (_$remoteT.vacancy()==0) {
                      SRuntime _$rt=SRuntimeWorker.getInst();
                      RuleMap _$rm=_$rt.getRuleMap(getRuleId());
                      int _$depRuleId=_$rm.getRemoteHeadDep(getRuleId());
                      EvalWithTable _$cmd=new EvalWithTable($epochId, _$depRuleId,_$remoteT,0);
                      boolean _$reuse = _$rt.sender().send(_$machineIdx,_$cmd);
                      if(!_$reuse)nullifyRemoteTable_head(_$machineIdx);
                  };};
              };
              ;
              break;
              }

          };
        } while (false);;
        return true;
    }

    public Remote_PATH$1_2_int_int_groupby0_id2 getRemoteTable_head(int _$rangeOrHash) {
        int _$machineIdx=$sliceMap.machineIndexFor(2, _$rangeOrHash);
        Remote_PATH$1_2_int_int_groupby0_id2 _$remoteT=remoteTable_head[_$machineIdx];
        if (_$remoteT==null) {
            _$remoteT=(Remote_PATH$1_2_int_int_groupby0_id2)TmpTablePool.get(worker.id(),Remote_PATH$1_2_int_int_groupby0_id2.class);
            remoteTable_head[_$machineIdx]=_$remoteT;
        };
        return _$remoteT;
    }

    public void nullifyRemoteTable_head(int _$machineIdx) {
        Remote_PATH$1_2_int_int_groupby0_id2 _$remoteT=remoteTable_head[_$machineIdx];
        assert _$remoteT!=null;
        remoteTable_head[_$machineIdx]=null;
    }

    public TableInst[] getDeltaTables() {
        return null;
    }

    public String toString() {
        String str="PATH$1(t,$Builtin.min(d)) :- Edge(0,t,d). epoch:"+$epochId;
        return str;
    }

}